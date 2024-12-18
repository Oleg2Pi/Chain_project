import React, { useEffect, useState } from "react";
import { useParams, Link, useNavigate } from "react-router-dom";
import { ApiGetFile, fetchProfile } from "../../api";
import HeaderBack from "../../components/HeaderBack";

import "../../styles/My/grid.css";
import "../../styles/My/resume.css";
import "../../styles/My/style.css";
import setting from "../../assets/icons/setting.png";
import search_work from "../../assets/elements/Rectangle_search_work.png";

const MyProfilePage = () => {
  const { myId } = useParams();
  const [person, setPerson] = useState(null);
  const navigate = useNavigate();
  

  useEffect(() => {

    const getUser = async () => {
      const response = await fetchProfile(myId);
      setPerson(response);
    };
    getUser();
  }, [myId]);

  if (!person) return <p>Loading...</p>;

  const handleClickPortfolio = () => {
    navigate(`/portfolio/${person.executorId}`)
  };

  const personImage = `${ApiGetFile}person/${myId}`;

  return (
    <main className="myprofile-page-container">
      <HeaderBack />
      <section>
        <div className="box">
          <div className="profile-picture-container">
            <img className="profile-picture" src={personImage} alt="" />
          </div>
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
          <img src={setting} alt="" className="setting-image" />
        </div>

        <Link to="#">
          <div className="relative-box">
            <div className="resume-block">
              <img src={search_work} alt="" className="img" />
              <p className="absolute-block">search work</p>
            </div>
          </div>
        </Link>

        <div className="small-buttons">
          <button id="back1"></button>
          <button id="back2"></button>
        </div>
      </section>
      <section>
        <div className="portfolio">
          <p>Портфолио</p>
          <img src={setting} alt="" className="setting-image" onClick={handleClickPortfolio}/>
        </div>
        <div className="video-grid">
          {person.works &&
            person.works.map((project, index) => {
              const imagePath = `${ApiGetFile} ${project.id}`;
              return (
                <div className="video-preview" key={index}>
                  <Link to={`/profile/work/${project.id}`}>
                    <div className="video-previw">
                      <img src={imagePath} alt="" className="image" />
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
      </section>
    </main>
  );
};

export default MyProfilePage;
