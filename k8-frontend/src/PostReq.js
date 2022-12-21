import { useNavigate } from "react-router-dom";

const PostReq = (baseURI, obj) => {
  console.log(JSON.stringify(obj));
  const result = fetch(baseURI, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(obj),
  }).then((res) => res.json());

  return result;
};
const PostReqAuth = (baseURI, obj) => {
  const logInfo = JSON.parse(window.localStorage.getItem("user"));
  const headers = {
    "Content-Type": "application/json",
    sessionId: logInfo.sessionId,
    id: logInfo.id,
  };

  const result = fetch(baseURI, {
    method: "POST",
    headers: headers,
    body: JSON.stringify(obj),
  });

  return result;
};

export { PostReq, PostReqAuth };
