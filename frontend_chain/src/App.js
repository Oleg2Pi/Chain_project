import React from "react";
import { Route, Routes } from 'react-router-dom';
import HomePage from './pages/HomePage';
import WorkPage from "./pages/Other/WorkPage";
import WorksPage from "./pages/Other/WorksPage";
import Footer from "./components/Footer";
import Authorization from "./components/Authorization";

import './styles/homePage/general.css';
import ProfilePage from "./pages/Other/ProfilePage";
import MyProfilePage from "./pages/My/MyProfilePage";
import PortfolioPage from "./pages/My/PortfolioPage";
import CreateWorkPage from "./pages/My/CreateWorkPage.js";

const App = () => {
  return (
    <div>
      <Routes>  
        <Route path="/authorization" element={<Authorization />} />
        <Route path="/" element={<HomePage />} /> 
        <Route path="/profile/work/:id" element={<WorkPage /> }/>
        <Route path="/profile/:id" element={<ProfilePage />}/>
        <Route path="/profile/portfolio/:id" element={<WorksPage />} />
        <Route path="/:myId" element={<MyProfilePage />} />
        <Route path="/portfolio/:id" element={<PortfolioPage />} />
        <Route path="/portfolio/:id/create" element={<CreateWorkPage />} />
      </Routes>
      <Footer />
    </div>
  );
};

export default App;