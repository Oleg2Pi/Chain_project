import React, { useRef, useState } from "react";
import "../../styles/My/CreateWork/body.css"; // Проверьте правильность пути к стилям
import HeaderBack from "../../components/HeaderBack";
import { useNavigate, useParams } from "react-router-dom";

const CreatePortfolioPage = () => {
  const fileInputRef = useRef(null);
  const [selectedFile, setSelectedFile] = useState(null);
  const [fileURL, setFileURL] = useState(null);
  const [fileType, setFileType] = useState(null);
  const [MYID, setMYID] = useState(null);
  const { id } = useParams();
  const navigate = useNavigate();

  const handleUploadClick = (event) => {
    event.preventDefault();
    fileInputRef.current.click(); // Программный клик по input[type="file"]
  };

  const handleFileChange = (event) => {
    const file = event.target.files[0];
    if (file) {
      const url = URL.createObjectURL(file); // Генерируем URL для загруженного файла
      setFileURL(url);
      setFileType(file.type); // Сохраняем тип файла
      setSelectedFile(file);
      alert(`Вы выбрали файл: ${file.name}`);
    }
  };

  const handleSubmit = async (event) => {
    const myId = await fetch.get("http://localhost:8080/profile",
      {
        method: "GET"
      }
    );

    if (myId == null) {
      throw new Error("Вы не зарегистрированы");
    }

    setMYID(myId);

    event.preventDefault();
    const projectName = event.target.projectName.value;
    const description = event.target.description.value;
    const dateAdded = new Date().getTime();
    console.log("Название проекта:", projectName);
    console.log("Описание:", description);
    console.log("Загруженный файл:", fileURL);

    const formData = new FormData();
    formData.append("executorId", id);
    formData.append("name", projectName);
    formData.append("dateAdded", dateAdded);
    formData.append("description", description);
    formData.append("type", fileType);

    try {
      const response = await fetch(
        "http://localhost:8080/api/portfolio/create",
        {
          method: "POST",
          body: formData,
        }
      );

      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(
          errorData.error || "Произошла ошибка при сохранении проекта."
        );
      }

      if (response.ok) {
        const responseData = await response.json();
        const workId = responseData.id;
        console.log("Work id: ", workId);
        console.log("File url:", selectedFile);

        const newFormData = new FormData();
        newFormData.append("file", selectedFile);
        newFormData.append("workId", workId);
        const responseSave = await fetch(
          "http://localhost:8080/api/files/upload",
          {
            method: "POST",
            body: newFormData,
          }
        );

        if (!responseSave.ok) {
          const errorData = await responseSave.json();
          throw new Error(errorData.error || "Ошибка при загрузке файла.");
        }

        if (responseSave) {
          alert("Работа успешно сохранена");
          navigate(`/${MYID}`);
        }
      }
    } catch (error) {
      console.error("Произошла ошибка при отправке данных: ", error);
      alert("Произошла ошибка при отправке данных.");
    }
  };

  return (
    <main className="create-page-container">
      <div className="header-create">
        <HeaderBack />
        <h1 className="head-h1">Добавить проект</h1>
      </div>
      <div>
        <div className="main-section">
          <form onSubmit={handleSubmit}>
            <div className="download-box">
              {!fileURL ? (
                <button
                  type="button"
                  className="upload-link"
                  onClick={handleUploadClick}
                >
                  загрузить файл
                </button>
              ) : (
                <div className="file-preview">
                  {fileType.startsWith("image/") ? (
                    <img
                      src={fileURL}
                      alt="Preview"
                      className="preview-image"
                    />
                  ) : fileType.startsWith("video/") ? (
                    <video controls className="preview-video">
                      <source src={fileURL} type={fileType} />
                      Ваш браузер не поддерживает видео.
                    </video>
                  ) : (
                    <p>
                      Файл не поддерживается для предварительного просмотра.
                    </p>
                  )}
                </div>
              )}
              <input
                type="file"
                className="file-input"
                ref={fileInputRef}
                onChange={handleFileChange}
                style={{ display: "none" }} // Скрываем элемент input
              />
            </div>
            <input
              id="project-name"
              type="text"
              name="projectName"
              placeholder="Название проекта..."
              required
            />
            <input
              id="description"
              type="text"
              name="description"
              placeholder="Описание"
              required
            />
            <div className="publication-box">
              <button type="submit" className="button">
                Сохранить
              </button>
            </div>
          </form>
        </div>
      </div>
    </main>
  );
};

export default CreatePortfolioPage;
