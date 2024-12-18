import React from "react";
import { useNavigate } from "react-router-dom";

const HeaderBack = () => {
  const navigate = useNavigate();

  const handleGoBack = () => {
    navigate(-1);
  };

  return (
    <header>
      <div className="arrow-exit" onClick={handleGoBack}>
        <div className="arrow-back">&#x2039;</div>
      </div>
    </header>
  );
};

export default HeaderBack;
