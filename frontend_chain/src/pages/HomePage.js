import React, { useState, useEffect } from "react";
import HeaderSearchWorks from "../components/HeaderSearchWorks";
import { Link } from "react-router-dom";
import { fetchUsersHomePage } from "../api";
import { ApiGetFile } from "../api";

import "../styles/homePage/main.css";

const HomePage = () => {
  const [persons, setPersons] = useState([]);

  useEffect(() => {
    const getUsers = async () => {
      const myId = await fetch("https://2e95-176-108-248-218.ngrok-free.app/api/profile", {
        method: "GET",
        credentials: "include",
      });
      console.log("MyId: ", myId);

      try {
        const data = await fetchUsersHomePage();
        if (Array.isArray(data)) {
          setPersons(data);
        } else {
          console.error("Полученные данные не являются массивом:", data);
        }
      } catch (error) {
        console.error("Ошибка при получении данных:", error);
      }
    };

    getUsers();
  }, []);

  return (
    <main className="home-page-container">
      <HeaderSearchWorks />
      <section>
        <div className="grid-box">
          {Array.isArray(persons) && persons.length > 0 ? (
            persons.map((profile, index) => {
              if (!profile.workFile) {
                return null; // Если работы нет, пропускаем этот профиль
              }

              const fullName = `${profile.firstName} ${profile.lastName}`;
              const profileImagePath = `${ApiGetFile}person/${profile.chatId}`;

              return (
                <div className="column-box" key={profile.chatId}>
                  <Link
                    className="work-photo"
                    to={`/profile/work/${profile.workId}`}
                  >
                    <div className="work-photo">
                      <img
                        className="photo"
                        src={`${ApiGetFile} ${profile.workId}`}
                        alt="Фотография проекта"
                      />
                    </div>
                  </Link>
                  <div className="information-box">
                    <Link to={`/profile/${profile.chatId}`}>
                      <div className="profile-info">
                        <img
                          className="image"
                          src={profileImagePath}
                          alt={fullName}
                        />
                        <div>{fullName}</div>
                      </div>
                    </Link>
                    <div className="indication-box">
                      <div className="box">
                        <i className="fa-regular fa-heart"></i>
                        <div className="likes">0</div>
                      </div>
                      <div className="box">
                        <i className="fa-solid fa-eye"></i>
                        <div className="viewers">0</div>
                      </div>
                    </div>
                  </div>
                </div>
              );
            })
          ) : (
            <p>Загрузка пользователей...</p> // Показываем сообщение во время загрузки
          )}
        </div>
      </section>
    </main>
  );
};

export default HomePage;
