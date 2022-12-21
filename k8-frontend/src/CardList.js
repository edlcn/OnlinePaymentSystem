import { useEffect, useState } from "react";
import { TiDelete } from "react-icons/ti";
import { IoMdAddCircle } from "react-icons/io";
import { PostReqAuth } from "./PostReq";
import Deposit from "./Deposit";
import { useNavigate, useParams } from "react-router-dom";

const CardList = ({ cards }) => {
  const logInfo = JSON.parse(window.localStorage.getItem("user"));
  const navigate = useNavigate();

  //const [cardsDup, setCardsDup] = useState(cards);

  const handleDelete = (card) => {
    fetch("http://mkb.express.edlcn/api/v1/cards/delete", {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json",
        id: logInfo.id,
        sessionId: logInfo.sessionId,
      },
      body: JSON.stringify({
        userId: logInfo.id,
        creditCard: card,
      }),
    })
      .then((res) => res.json())
      .then((data) => {
        if (data.status != "200") {
          alert(data.status);
          
        } else {
          alert("Card Deleted Succesfully.");
        }
      });
  };

  const handleDeposit = (card) => {
    console.log(card);

    navigate("/deposit", {
      state: {
        card,
      },
    });
  };

  return (
    <div className="card-list">
      {cards.map((card) => (
        <div className="card-preview" key={card.number} val={card.number}>
          <button onClick={() => handleDelete(card)}>
            <TiDelete />
          </button>
          <button onClick={() => handleDeposit(card)}>
            <IoMdAddCircle />
          </button>
          <h2>{card.number}</h2>
          <p>{card.name}</p>
          <p>{card.cva}</p>
        </div>
      ))}
    </div>
  );
};

export default CardList;
