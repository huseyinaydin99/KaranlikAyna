import React, { useEffect, useState } from "react";
import axios from "axios";
import { signUp } from "./api";

export function SignUp() {
  //getValue();
  const [username, setUsername] = useState();
  const [email, setEmail] = useState();
  const [password, setPassword] = useState();
  const [passwordRepeat, setPasswordRepeat] = useState();
  const [apiProgress, setApiProgress] = useState(false);
  const [successMessage, setSuccessMessage] = useState();
  const [value, setValue] = useState();
  const [errors, setErrors] = useState({});
  const [generalError, setGeneralError] = useState();

  useEffect(() => {
    setErrors({});
  }, [username]);

  const onSubmit = async (event) => {
    event.preventDefault(); //formdan gelen event'i alır ve tarayıcının işlmesini önler böylelikle form submit olunca tarayıcadaki sayfa yenilenmez.!
    setValue(1);
    setGeneralError();
    setSuccessMessage();
    setApiProgress(true);

    try {
      const response = await signUp({username, email, password});
      setSuccessMessage(response.data.message);
      console.log(response.data.message);
    }
    catch(axiosError) {
      console.log(axiosError);
      if(axiosError.response?.data && axiosError.response.data.status === 400){
        setErrors(axiosError.response.data.validationErrors);
      }else{
        setGeneralError("Bilinmeyen hata meydana geldi lütfen tekrar deneyiver gardeşim.");
      }
      //setSuccessMessage(undefined);
    }
    finally {
      setApiProgress(false);
    }
    /*const response = signUp({username, email, password})
    .then((response) => {
      setSuccessMessage(response.data.message);
    })
    .catch((err) => {
      setSuccessMessage(undefined);
    })
    .finally(() => {
      setApiProgress(false);
    });*/
    
    /*axios.post("http://localhost:8080/api/v1/users", {
      username,
      email,
      password,
    }).then((response) => {
      setSuccessMessage(response.data.message);
    }).catch((err) => {
      setSuccessMessage(undefined);
    }).finally(() => {
      setApiProgress(false);
    });*/
  };
  return (
    <div className="container">
      <div className="col-lg-6 offset-lg-3 col-sm-8 offset-sm-2">
        <form className="card" onSubmit={onSubmit}>
          <div className="text-center card-header">
            <h1>Kaydol</h1>
          </div>
          <div className="card-body">
            <div className="mb-3">
              <label htmlFor="username" className="form-label">
                Kullanıcı Adı
              </label>
              <input
                id="username"
                type="text"
                className={errors.username ? "form-control is-invalid" : "form-control"}
                onChange={(event) => {
                  setUsername(event.target.value);
                  //setErrors({});
                }}
              />
            </div>
            <div className="invalid-feedback">
              {errors.username}
            </div>
            <div className="mb-3">
              <label htmlFor="email" className="form-label">
                E-Posta
              </label>
              <input
                id="email"
                type="text"
                className="form-control"
                onChange={(event) => {
                  setEmail(event.target.value);
                }}
              />
            </div>
            <div className="mb-3">
              <label htmlFor="password" className="form-label">
                Şifre
              </label>
              <input
                id="password"
                type="password"
                className="form-control"
                onChange={(event) => {
                  setPassword(event.target.value);
                }}
              />
            </div>
            <div className="mb-3">
              <label htmlFor="passwordRepeat" className="form-label">
                Şifre Tekrarı
              </label>
              <input
                id="passwordRepeat"
                type="password"
                className="form-control"
                onChange={(event) => {
                  setPasswordRepeat(event.target.value);
                }}
              />
            </div>
            {successMessage && <div className="alert alert-success">{successMessage}</div>}
            {generalError && <div className="alert alert-danger">{generalError}</div>}
            {/*
            {!successMessage && value > 0 && <div className="alert alert-danger">Hata oluştu.</div>}
            */}
            <div>
              <button
                onClick={() => setValue(1)}
                disabled={!password || password !== passwordRepeat}
                className="btn btn-primary">
                  {apiProgress && <span><span className="spinner-border spinner-border-sm" aria-hidden="true"></span> Kaydol</span>}
                  {!apiProgress && "Kaydol"}
              </button>
            </div>
          </div>
        </form>
      </div>
    </div>
  );
}
