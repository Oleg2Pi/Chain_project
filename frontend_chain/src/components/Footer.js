import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import layer from "../assets/icons/layers.png";
import message from "../assets/icons/message.png";
import pen from "../assets/icons/pen.png";
import profile from "../assets/icons/profile.png";
import "../styles/homePage/navigation.css";
import "../styles/homePage/main.css";
import { fetchProfile } from "../api";

const Footer = () => {
  const [person, setPerson] = useState(null);
  const [isOpen, setIsOpen] = useState(false);
  const [MYID, setMYID] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const getPerson = async () => {
      const myId = await fetch("https://3527-176-108-248-218.ngrok-free.app/profile",
        {
          method: "GET"
        }
      );
      console.log("MyId: ", myId)

      if (myId == null) {
        throw new Error("Вы не зарегистрированы");
      }

      setMYID(myId);
      const response = await fetchProfile(myId);
      setPerson(response);
    };
    getPerson();
  }, []);

  const handleToggle = () => {
    setIsOpen((prev) => !prev); // Изменение состояния
  };

  const handleClose = () => {
    setIsOpen(false);
  };

  const handleClickCreate = () => {
    handleClose();
    navigate(`/portfolio/${person.executorId}/create`);
  };

  return (
    <main className="main">
      <footer>
        <nav className="destination">
          <Link to="/">
            <img className="icon" src={layer} alt="" />
            <div className="label">Главная</div>
          </Link>
          <Link to="#">
            <img className="icon" src={message} alt="" />
            <div className="label">Обсуждения</div>
          </Link>
          <div
            onClick={handleToggle}
            className={`plus-icon ${isOpen ? "open" : ""}`}
          >
            <i className="fa-solid fa-plus"></i>
          </div>
          <Link to="#">
            <img className="icon" src={pen} alt="" />
            <div className="label">Академия</div>
          </Link>
          <Link to={`/${MYID}`}>
            <img className="icon" src={profile} alt="" />
            <div className="label">Профиль</div>
          </Link>
        </nav>

        {isOpen && <div className="overlay" onClick={handleClose} />}

        <div className={`main-buttons-container ${isOpen ? "show" : ""}`}>
          <button className="main-button" onClick={handleClose}>
            Создать заказ
          </button>
          <button className="main-button" onClick={handleClickCreate}>
            Создать проект
          </button>
        </div>
      </footer>
    </main>
  );
};

export default Footer;
