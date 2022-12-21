import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { PostReqAuth } from "./PostReq";

const MerchantPayment = () => {
  const [cards,setCards] = useState(null);
  const [paymentParams,setPaymentParams] = useState(null);
  const [isActive, setIsActive] = useState(false);
  const [activeCard, setActiveCard] = useState("");
  const { token } = useParams();
  const logInfo = JSON.parse(window.localStorage.getItem("user"));
  const navigate = useNavigate();

  useEffect(() => {
    fetch("http://mkb.express.edlcn/api/v1/cards?uid=" + logInfo.id, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        sessionId: logInfo.sessionId,
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
    fetch("http://mkb.express.edlcn/api/v1/merchantPayment/"+token,{
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            sessionId: logInfo.sessionId,
            id: logInfo.id
          }
    }).then(res => res.json()).then(data => {
        if (data.status !== "200"){
            alert(data.status);
            return;
        }
        console.log(data.returnObject);
        setPaymentParams(data.returnObject);
        console.log(paymentParams);

    })
    
  },[]);

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

  

  const handlePayment = () => {
    if (!isActive) {
      alert("Select a card!");
      return;
    }
    const creditCard = cards.filter((card) => card.number == activeCard)[0];
    const toBeSent = creditCard ;
    PostReqAuth("http://mkb.express.edlcn/api/v1/merchantPayment/complete?token="+token, toBeSent)
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
            console.log(creditCard);
          alert("Payment failed.\n" + data.status);

        }
      });
  };

  return (
    <div className="my-cards">
      <div className="cost">
        {paymentParams && <h2>Dear {paymentParams.username} please choose a card to complete your payment at {paymentParams.merchantName} which costs ${paymentParams.amount} .</h2>}
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

      
      {(cards !== null && cards !== []) && (<button onClick={handlePayment}>PAY</button>)}
    </div>
  );
};

export { MerchantPayment };
