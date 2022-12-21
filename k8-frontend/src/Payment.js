import { useEffect, useState } from "react";
import CardList from "./CardList";
import { Navigate, useNavigate } from "react-router-dom";
import { ReactDOM } from "react";
import { PostReqAuth } from "./PostReq";
import AddCard from "./AddCard";

const Payment = () => {
  const logData = JSON.parse(window.localStorage.getItem("user"));
  const [cards, setCards] = useState(null);
  const [isActive, setIsActive] = useState(false);
  const [activeCard, setActiveCard] = useState("");
  const navigate = useNavigate();
  const price = randomCost(50, 500).toFixed(2);

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
        if (data.status == "400:TOKEN EXPIRED!") {
          window.localStorage.removeItem("isLoggedIn");
          window.localStorage.removeItem("user");
          navigate("/login");
          alert("Token expired, please login again.");
        } else {
          console.log(data);
          setCards(data.returnObject);
        }
      });
  }, []);

  const handleClick = (event) => {
    if (event.currentTarget.getAttribute("val") == activeCard) {
      event.currentTarget.classList.remove("card-preview-active");
      setIsActive(false);
      setActiveCard("");
      return;
    }
    if (isActive) {
      document
        .getElementById(activeCard)
        .classList.remove("card-preview-active");
      event.currentTarget.classList.add("card-preview-active");
      setActiveCard(event.currentTarget.getAttribute("val"));
      return;
    }
    event.currentTarget.classList.add("card-preview-active");
    setActiveCard(event.currentTarget.getAttribute("val"));
    setIsActive(true);
  };

  function randomCost(min, max) {
    return Math.random() * (max - min + 1) + min;
  }

  const handlePayment = () => {
    if (!isActive) {
      alert("Select a card!");
      return;
    }
    const creditCard = cards.filter((card) => card.number == activeCard)[0];
    const toBeSent = { price, creditCard };
    PostReqAuth("http://mkb.express.edlcn/api/v1/payment", toBeSent)
      .then((res) => res.json())
      .then((data) => {
        if (data.status == "200") {
          alert("Payment was successfull.");
          navigate("/myPayments");
        } else if (data.status == "400:TOKEN EXPIRED!") {
          window.localStorage.removeItem("isLoggedIn");
          window.localStorage.removeItem("user");
          navigate("/login");
          alert("Token expired, please login again.");
        } else {
          alert("Payment failed.\n" + data.status);
        }
      });
  };

  return (
    <div className="my-cards">
      <div className="cost">
        <h2>Total of: {price} $</h2>
      </div>
      <div className="card-list">
        {cards &&
          cards.map((card) => (
            <div
              onClick={handleClick}
              className="card-preview"
              key={card.number}
              val={card.number}
              id={card.number}
            >
              <h2>{card.number}</h2>
              <p>{card.name}</p>
              <p>{card.cva}</p>
            </div>
          ))}
      </div>

      {!cards && (
        <button onClick={() => navigate("../addCard")}>ADD CARD</button>
      )}
      <button onClick={handlePayment}>PAY</button>
    </div>
  );
};

export default Payment;
