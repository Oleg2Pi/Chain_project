import React, { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import { ApiGetFile, fetchWorksExecutor } from "../../api";

import "../../styles/Other/Portfolio/body.css";
import HeaderBack from "../../components/HeaderBack";

const PortfolioPage = () => {
  const [works, setWorks] = useState([]);
  const { id } = useParams();

  useEffect(() => {
    const getWorks = async (id) => {
      try {
        const data = await fetchWorksExecutor(id);
        setWorks(data); // устанавливаем данные
      } catch (error) {
        console.log("Ошибка при получении работы", error);
      }
    };

    if (id) {
      getWorks(id); // загружаем данные при наличии id
    } else {
      console.log("Нет id работы");
    }
  }, [id]);

  return (
    <main className="profile-page-container">
      <HeaderBack />
      <section>
        <div className="video-grid">
          {works && works.length > 0 ? (
            works.map((work, index) => {
              const imagePath = `${ApiGetFile} ${work.id}`;
              return (
                <div className="video-preview" key={index}>
                  <Link to={`/profile/work/${work.id}`}>
                    <img className="image" src={imagePath} alt="" />
                  </Link>
                  <div className="video-info">
                    <p>{work.name}</p>
                  </div>
                </div>
              );
            })
          ) : (
            <p>Loading...</p>
          )}
        </div>
      </section>
    </main>
  );
};

export default PortfolioPage;
