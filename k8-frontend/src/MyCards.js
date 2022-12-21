import { useState, useEffect } from "react";
import { Navigate, useNavigate } from "react-router-dom";
import CardList from "./CardList";
import AddCard from "./AddCard";

const MyCards = () => {
  const [cards, setCards] = useState(null);

  const navigate = useNavigate();
  const logData = JSON.parse(window.localStorage.getItem("user"));

  useEffect(() => {
    fetch("http://mkb.express.edlcn/api/v1/cards?uid=" + logData.id, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        sessionId: logData.sessionId,
      },
    })
      .then((res) => res.json())
      .then((data) => {
        if (data.status === "200") {
          console.log(data);
          setCards(data.returnObject);
        } else if (data.status == "400:TOKEN EXPIRED!") {
          window.localStorage.removeItem("isLoggedIn");
          window.localStorage.removeItem("user");
          navigate("/login");
          alert("Token expired, please login again.");
        } else {
          alert("Error loading data.\n" + data.status);
        }
      });
  }, []);

  return (
    <div className="my-cards">
      {cards ? <CardList cards={cards} /> : <div>Loading...</div>}

      <button onClick={() => navigate("../addCard")}>ADD</button>
    </div>
  );
};

export default MyCards;
