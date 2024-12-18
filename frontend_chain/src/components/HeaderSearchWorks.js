import React from 'react';
import search from '../assets/icons/search.png';
import { Link } from 'react-router-dom';

import '../styles/HeaderBack.css';

const HeaderSearchWorks = () => {
    return (
        <header>
            <section className="searching-box">
                <div className="input-wrapper">
                    <img src={search} alt="" />
                    <input type="text" placeholder="Поиск" />
                </div>
            </section>
            <section>
                <nav className="navigation-panel">
                    <ul className="menu">
                        <li><Link to="#">Все</Link></li>
                        <li><Link to="#">Фотография</Link></li>
                        <li><Link to="#">Видео</Link></li>
                        <li><Link to="#">SMM</Link></li>
                        <li><Link to="#">Монтаж</Link></li>
                    </ul>
                </nav>
            </section>
        </header>
    );
};

export default HeaderSearchWorks;