import React, { useEffect, useState, useMemo } from "react";
import axios from "axios";
import { signUp } from "./api";
import { Input } from "./components/input";
import { useTranslation } from "react-i18next";
import { Alert } from "@/shared/components/Alert";
import { Spinner } from "@/shared/components/Spinner";

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
  const { t } = useTranslation();

  useEffect(() => {
    setErrors(function (lastError) {
      return {
        ...lastError,
        username: undefined,
      };
    });
  }, [username]);

  useEffect(() => {
    setErrors(function (lastError) {
      return {
        ...lastError,
        email: undefined,
      };
    });
  }, [email]);

  useEffect(() => {
    setErrors(function (lastError) {
      return {
        ...lastError,
        password: undefined,
      };
    });
  }, [password]);

  useEffect(() => {
    setErrors(function (lastError) {
      return {
        ...lastError,
        passwordRepeat: undefined,
      };
    });
  }, [passwordRepeat]);

  const onSubmit = async (event) => {
    event.preventDefault(); //formdan gelen event'i alır ve tarayıcının işlmesini önler böylelikle form submit olunca tarayıcadaki sayfa yenilenmez.!
    setValue(1);
    setGeneralError();
    setSuccessMessage();
    setApiProgress(true);

    try {
      const response = await signUp({ username, email, password });
      setSuccessMessage(response.data.message);
      console.log(response.data.message);
    } catch (axiosError) {
      console.log(axiosError);
      if (axiosError.response?.data) {
        if (axiosError.response.data.status === 400) {
          setErrors(axiosError.response.data.validationErrors);
        } else {
          setGeneralError(axiosError.response.data.message);
        }
      } else {
        setGeneralError(t("genericError"));
      }
      //setSuccessMessage(undefined);
    } finally {
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

  const passwordRepeatError = useMemo(() => {
    if (password && password !== passwordRepeat) {
      return t("passwordMismatch");
    }
    return "";
  }, [password, passwordRepeat]); // useMemo burada password ve passwordRepeat alanlarında değişiklik olmuşsa kod bloğu çalışıyor ki her render olduğında aynı işlemi yapmasın diye.

  return (
    <div className="container">
      <div className="col-lg-6 offset-lg-3 col-sm-8 offset-sm-2">
        <form className="card" onSubmit={onSubmit}>
          <div className="text-center card-header">
            <h1>{t("signUp")}</h1>
          </div>
          <div className="card-body">
            <Input
              id="username"
              label={t("username")}
              error={errors.username}
              onChange={(event) => setUsername(event.target.value)}
            />
            <Input
              id="email"
              label={t("email")}
              error={errors.email}
              onChange={(event) => setEmail(event.target.value)}
            />
            <Input
              id="password"
              label={t("password")}
              error={errors.password}
              onChange={(event) => setPassword(event.target.value)}
              type="password"
            />
            <Input
              id="passwordRepeat"
              label={t("passwordRepeat")}
              error={passwordRepeatError}
              onChange={(event) => setPasswordRepeat(event.target.value)}
              type="password"
            />

            {successMessage && <Alert>{successMessage}</Alert>}
            {generalError && <Alert styleType="danger">{generalError}</Alert>}
            {/*
            {!successMessage && value > 0 && <div className="alert alert-danger">Hata oluştu.</div>}
            */}

            <div>
              <button
                onClick={() => setValue(1)}
                disabled={!password || password !== passwordRepeat}
                className="btn btn-primary"
              >
                {apiProgress && <Spinner sm={true} />}
                {!apiProgress && t("signUp")}
              </button>
            </div>
          </div>
        </form>
      </div>
    </div>
  );
}
