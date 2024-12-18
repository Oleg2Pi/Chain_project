import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";

const Authorization = () => {
  const navigate = useNavigate();

  useEffect(() => {
    // Подготовка к работе с Telegram Web App
    if (window.Telegram && window.Telegram.WebApp) {
      window.Telegram.WebApp.ready(); // Установите готовность приложения

      // Получение данных о пользователе
      const user = window.Telegram.WebApp.initDataUnsafe.user;
      console.log(user);

      const authorizeUser = async () => {
        try {
          const success = await fetch("https://3527-176-108-248-218.ngrok-free.app/api/login", {
            method: "POST",
            credentials: "include",
            headers: {
              "Content-type": "application/json",
            },
            body: JSON.stringify({
              "chatId": user.id,
            }),
          });

          if (success.ok) {
            console.log("Login success");
            // setTimeout(() => {
            //   navigate('/');
            // }, 1000);
          } else {
            const errorMessage = await success.text(); // Получаем текст ошибки
            throw new Error(`Login failed: ${success.status} ${errorMessage} id: ${user.id}`);
          }
        } catch (err) {
          console.error(`Don't longin id: ${user.id}, ${err.message}`);
          throw new Error(err.message);
        }
      };

      authorizeUser();
    } else {
      console.error("Telegram Web App is not loaded.");
    }
  }, [navigate]);

  return (
    <body>
      <div>
        <h1>Authorization...</h1>
        {/* Остальная часть вашего компонента */}
      </div>
    </body>
  );
};

export default Authorization;
