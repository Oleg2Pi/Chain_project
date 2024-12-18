import asyncio
import logging
import sys
from os import getenv

import requests
from aiogram import Dispatcher, Bot, types, F
from aiogram.filters import CommandStart
from aiogram.types import Message

bot = Bot(token=getenv('BOT_TOKEN'))
dp = Dispatcher()

person = {}
image = {}
image_file = {}
url = 'http://backend:8080/api'


async def get_profile_photo_path(user_id: int) -> str | None:
    profile_photo = await bot.get_user_profile_photos(user_id)
    photo = None

    if profile_photo.total_count > 0:
        large_photo = profile_photo.photos[0][-1]
        file_id = large_photo.file_id

        photo_file = await bot.get_file(file_id)
        file_path = photo_file.file_path

        photo = await bot.download_file(file_path)

    return photo


@dp.message(CommandStart())
async def command_start_handler(message: Message) -> None:
    global image_file
    user = message.from_user
    profile_photo = await get_profile_photo_path(user.id)

    first_name = user.first_name
    last_name = user.last_name
    username_tg = user.username
    chat_id = message.chat.id

    person["chatId"] = chat_id
    person["firstName"] = first_name
    person["lastName"] = last_name
    person["usernameTG"] = username_tg

    image["chatId"] = chat_id
    image_file = {'file': ("image_of_profile.png", profile_photo, "image/png")}
    image["typeFile"] = ".png"

    await message.answer(
        "Нажмите кнопку ниже, чтобы поделиться своим номером телефона:",
        reply_markup=await phone_request_keyboard()
    )


async def phone_request_keyboard():
    contact_button = types.KeyboardButton(text="Поделиться номером", request_contact=True)
    keyboard = types.ReplyKeyboardMarkup(resize_keyboard=True, keyboard=[[contact_button]])  # Задаем список кнопок
    return keyboard


@dp.message(F.contact)
async def handle_contact(message: types.Message):
    person["phone"] = message.contact.phone_number

    await send_role_selection_message(message.chat.id)


async def send_role_selection_message(chat_id):
    executor_button = types.KeyboardButton(text="Исполнитель")
    customer_button = types.KeyboardButton(text="Заказчик")

    keyboard = types.ReplyKeyboardMarkup(resize_keyboard=True, keyboard=[[executor_button], [customer_button]])

    await bot.send_message(chat_id, "Выберите вашу роль:", reply_markup=keyboard)


@dp.message(lambda message: message.text in ["Исполнитель", "Заказчик"])
async def handle_role_selection(message: types.Message):
    chat_id = message.chat.id
    if message.text == 'Заказчик':
        await bot.send_message(chat_id, "На данный момент заказчик находится в разработке, доступен исполнитель")
    elif message.text == "Исполнитель":
        person["work"] = "executor"

        try:
            response_person = requests.post(url= url + "/person/create", data=person)
            response_image = requests.post(url= url + "/files/image", data=image, files=image_file)

            if response_person.status_code != 200:
                print(f"Person didn't save")

            if response_image.status_code != 200:
                print(f"Image of person didn't save")

        except Exception as e:
            print("Error", e)

        await bot.send_message(chat_id, "Ваш профиль создан. Нажмите на кнопку \"open app\", чтобы перейти в mini-app.")


async def main() -> None:
    await dp.start_polling(bot)


if __name__ == "__main__":
    logging.basicConfig(level=logging.DEBUG, stream=sys.stdout)

    try:
        asyncio.run(main())
    except KeyboardInterrupt:
        print("Bot stopped.")
