import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { activateUser } from "./api";
import { Alert } from "@/shared/components/Alert";
import { Spinner } from "@/shared/components/Spinner";

export function Activation() {

  const { apiProgress, data, error } = useRouteParamApiRequest(
    "token",
    activateUser
  );

  return (
    <>
      {apiProgress && (
        <Alert styleType="secondary" center>
          <Spinner />
        </Alert>
      )}
      {data?.message && <Alert>{data.message}</Alert>}
      {error && <Alert styleType="danger">{error}</Alert>}
    </>
  );
}
