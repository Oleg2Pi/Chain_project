import axios from "axios";

// Создаем экземпляр axios с базовым URL
const api = axios.create({
    baseURL: 'https://2e95-176-108-248-218.ngrok-free.app/api/',
});

// Функция для обработки ошибок
const handleApiError = (error) => {
    if (error.response) {
        // Сервер ответил с кодом, который выходит за пределы 2xx
        console.error('Ошибка ответа:', error.response.data);
        throw new Error(error.response.data.message || 'Ошибка API');
    } else if (error.request) {
        // Запрос был сделан, но ответ не был получен
        console.error('Ошибка запроса:', error.request);
        throw new Error('Ошибка сети. Пожалуйста, попробуйте позже.');
    } else {
        // Что-то произошло при настройке запроса
        console.error('Ошибка', error.message);
        throw new Error('Неизвестная ошибка. Пожалуйста, попробуйте позже.');
    }
};

// Функции для выполнения запросов к API с обработкой ошибок
export const fetchUsersHomePage = async () => {
    try {
        const response = await api.get('/person/home');
        return response.data; // Возвращаем данные участников
    } catch (error) {
        handleApiError(error); // Обрабатываем ошибку
    }
};

export const fetchWorkPage = async (id) => {
    try {
        const response = await api.get(`/portfolio/work/${id}`);
        return response.data; // Возвращаем данные работы
    } catch (error) {
        handleApiError(error); // Обрабатываем ошибку
    }
};

export const fetchProfile = async (id) => {
    try {
        const response = await api.get(`/person/${id}`);
        return response.data; // Возвращаем данные человека
    } catch (error) {
        handleApiError(error); // Обрабатываем ошибку
    }
};

export const fetchWorksExecutor = async (id) => {
    try {
        const response = await api.get(`/portfolio/${id}`);
        return response.data;
    } catch (error) {
        handleApiError(error);
    }
}

export const ApiGetFile = `https://2e95-176-108-248-218.ngrok-free.app/api/files/`;

export default api;