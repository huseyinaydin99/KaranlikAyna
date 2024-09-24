import { useParams } from "react-router-dom";

export function Activation() {
  const { token } = useParams(); //path'den gelen parametreyi alır.
  return <div>Activation Page</div>;
}