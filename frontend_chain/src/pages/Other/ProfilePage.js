import React, { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import { ApiGetFile, fetchProfile } from "../../api";

import "../../styles/Other/Profile/grid.css";
import "../../styles/Other/Profile/resume.css";
import HeaderBack from "../../components/HeaderBack";

const ProfilePage = () => {
  const { id } = useParams();
  const [person, setPerson] = useState(null);

  useEffect(() => {
    const getPerson = async () => {
      const response = await fetchProfile(id);
      setPerson(response);
    };
    getPerson();
  }, [id]);

  if (!person) return <p>Loading...</p>;

  const personImage = `${ApiGetFile}person/${person.personChatId}`;

  return (
    <main className="profile-page-container">
      <HeaderBack />
      <section>
        <div className="box">
          <img className="profile-picture" src={personImage} alt="" />
          <div className="name-profession-box">
            <div className="name">{person.personFirstName || "Имя"}</div>
            <div className="second-row">
              <div className="profession">
                {person.executorActivityArea || "Нет направления"} •
              </div>
              <div className="status">
                {person.executorStatusCategory || "Нет статуса"}
              </div>
            </div>
          </div>
        </div>

        <Link to="#">
          <div className="relative-box">
            <div className="absolute-block">☰</div>
            <div className="resume-block">Резюме</div>
          </div>
        </Link>

        <div className="button-box">
          <button className="subscribe-button">Подписаться</button>
          <button className="write-button">Написать</button>
        </div>
      </section>
      <section>
        <div className="portfolio">Портфолио</div>
        <div className="video-grid">
          {person.works &&
            person.works.map((project, index) => {
              return (
                <div className="video-preview" key={index}>
                  <Link to={`/profile/work/${project.id}`}>
                    <div className="video-previw">
                      <img src={`${ApiGetFile} ${project.id}`} alt="" className="image" />
                      <div className="video-info">
                        <p>{project.description || "Описание проекта"}</p>
                      </div>
                    </div>
                  </Link>
                </div>
              );
            })}
        </div>

        {/* <div className="video-grid">
        <div className="video-preview">
          <img className="image" src="" alt="" />
          <div className="video-info">
            <p>Съемка клипа местному музыканту</p>
          </div>
        </div>
      </div> */}

        <section className="more-section">
          <Link to={`/profile/portfolio/${person.executorId}`}>
            <button className="more-button">Больше работ</button>
          </Link>
        </section>
      </section>
    </main>
  );
};

export default ProfilePage;
