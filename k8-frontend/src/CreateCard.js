import { useState } from "react";
import { PostReq } from "./PostReq";

const CreateCard = () => {
  const nameArray = [
    "Ali",
    "Ahmet",
    "Funda",
    "Fatih",
    "Emirhan",
    "Burak",
    "Hakan",
    "Mahmut",
    "Taner",
    "Volkan",
    "Alperen",
    "Alp",
    "Orbay",
    "Rauf",
  ];
  const surname = [
    "Kalin",
    "Ak",
    "Can",
    "Yilmaz",
    "Karapinar",
    "Akpinar",
    "Kaya",
    "Tas",
    "Orgun",
    "Gorgulu",
    "Shelby",
    "White",
    "Soprano",
    "Moltisanti",
    "Gualtieri",
  ];
  const banks = [
    "Akbank",
    "Denizbank",
    "Yapi Kredi",
    "Emlakbank",
    "Sekerbank",
    "Is Bankasi",
  ];

  const randomNumberGenerator = () => {
    return Math.floor(Math.random() * 10).toString();
  };

  const phoneGenerator = () => {
    let phone = "+90535";
    for (let x = 0; x < 7; x++) {
      phone += randomNumberGenerator();
    }
    return phone;
  };

  const cardNumberGenerator = () => {
    let number = "";
    for (let x = 0; x < 16; x++) {
      number += randomNumberGenerator();
    }
    return number;
  };

  const cardDateGenerator = () => {
    return (
      Math.floor(Math.random() * 12 + 1).toString() +
      "/" +
      Math.floor(Math.random() * 10 + 23).toString()
    );
  };

  const fullnameGenerator = () => {
    return (
      nameArray[Math.floor(Math.random() * nameArray.length)] +
      " " +
      surname[Math.floor(Math.random() * surname.length)]
    );
  };

  const mailGenerator = (name) => {
    return name + "@gmail.com";
  };

  const bankGenerator = () => {
    return banks[Math.floor(Math.random() * 6)];
  };

  const cvvGenerator = () => {
    return Math.floor(Math.random() * 900 + 100);
  };

  const identityGenerator = () => {
    let identity = "";
    for (let x = 0; x < 11; x++) {
      identity += randomNumberGenerator();
    }
    return identity;
  };

  const balanceGenerator = () => {
    return (Math.random() * 1000).toFixed(2);
  };

  const ibanGenerator = () => {
    let init = "TR20";
    for (let x = 0; x < 22; x++) {
      init += randomNumberGenerator();
    }
    return init;
  };

  const handleClick = () => {
    const myCompany = {
      name: "MKB Express",
      number: "4284525063774517",
      date: "11/33",
      bank: "Is Bankasi",
      identity: "42558569441",
      cvv: 645,
      mail: "MKBExpress@gmail.com",
      phone: "+905337412588",
      balance: 0.0,
      iban: "TR204000566588456322869000",
    };
    PostReq("http://mkb.express.edlcn/classified/ccdb", myCompany);
    const merchantCompany = {
      name: "YABE LIMITED",
      number: "4284525063774553",
      date: "12/24",
      bank: "Is Bankasi",
      identity: "55678833330",
      cvv: 330,
      mail: "YABE@gmail.com",
      phone: "+905337412344",
      balance: 0.0,
      iban: "TR204000566588456327453759",
    };
    PostReq("http://mkb.express.edlcn/classified/ccdb", merchantCompany);

    for (let x = 0; x < 30; x++) {
      const name = fullnameGenerator();
      const number = cardNumberGenerator();
      const date = cardDateGenerator();
      const bank = bankGenerator();
      const identity = identityGenerator();
      const cvv = cvvGenerator();
      const mail = mailGenerator(name.replace(" ", "."));
      const phone = phoneGenerator();
      const balance = balanceGenerator();
      const iban = ibanGenerator();

      const toBeSent = {
        name,
        number,
        date,
        bank,
        identity,
        cvv,
        mail,
        phone,
        balance,
        iban,
      };

      PostReq("http://mkb.express.edlcn/classified/ccdb", toBeSent);
    }
  };

  return (
    <div className="add-card-admin">
      <button onClick={handleClick}>CREATE 30</button>
    </div>
  );
};

export default CreateCard;
