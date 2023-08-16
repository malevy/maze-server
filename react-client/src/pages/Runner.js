import React from "react";
import { useNavigate } from "react-router-dom";
import { useStore } from "../contexts/Store.js";
import "./Runner.css";
import Panel from "../components/Panel.js";
import mazeServer from "../gateways/mazeserver.js";
import directions from "../services/Directions.js";
import LoadingIndicator from "../components/LoadingIndicator.js";

function Runner() {
  const [showBackButton, setShowBackButton] = React.useState(true);
  const [backDirection, setBackDirection] = React.useState("");
  const { storeNavigation, currentCell, from } = useStore();
  const [panelData, setPanelData] = React.useState([]);
  const [loading, setLoading] = React.useState(false);
  const navigate = useNavigate();

  /* eslint-disable react-hooks/exhaustive-deps */
  React.useEffect(() => {
    // no currentCell means that we navigated to the runner page
    // directly. most likely by refreshing the browser
    if (!currentCell) {
      navigate("/");
    } else {
      render(currentCell, from);
    }
  }, []);

  async function go(direction) {
    console.log(`moving ${direction}`);
    if (!direction) return;
    if (direction === "exit") {
      navigate("/escaped");
    }

    const link = currentCell.links.find((lnk) => lnk.rel === direction);
    if (!link) return;

    setLoading(true);
    const nextCell = await mazeServer.goToCell(link.href);
    storeNavigation(nextCell, direction);
    setLoading(false);
    render(nextCell, direction);
  }

  function render(cell, comingFrom) {
    if (hasExit(cell)) renderExit(cell);
    else renderCell(cell, comingFrom);
  }

  function renderCell(cell, comingFrom) {
    console.log({ cell });
    const possibleDirections = directions.getDirectionsComingFrom(comingFrom);
    const newPanels = [];
    for (let i = 0; i < 3; i++) {
      const link = cell.links.find((h) => h.rel === possibleDirections[i]);
      if (link) {
        console.log(possibleDirections[i] + " is a door");
        newPanels.push({ img: doorImages[i], direction: link.rel });
      } else {
        console.log(possibleDirections[i] + " is a wall");
        newPanels.push({ img: wallImages[i], direction: "" });
      }
    }
    setBackDirection(directions.oppositeOf(comingFrom));
    setPanelData(newPanels);
  }

  function renderExit() {
    const newPanels = [];
    newPanels.push({ img: wallImages[0], direction: "" });
    newPanels.push({ img: doorImages[1], direction: "exit" });
    newPanels.push({ img: wallImages[2], direction: "" });
    setPanelData(newPanels);
    setShowBackButton(false);
  }

  function hasExit(cell) {
    return cell.links.some((link) => link.rel === directions.EXIT);
  }

  const doorImages = [
    require("../assets/left-wall-door.jpg"),
    require("../assets/front-wall-door.jpg"),
    require("../assets/right-wall-door.jpg"),
  ];
  const wallImages = [
    require("../assets/left-wall-blank.jpg"),
    require("../assets/front-wall-blank.jpg"),
    require("../assets/right-wall-blank.jpg"),
  ];

  if (loading) {
    return (
      <article>
        <div id="view">
          <LoadingIndicator id="loadingIndicator" />
        </div>
      </article>
    );
  }

  return (
    <article>
      <div id="view">
        <Panel
          position="left"
          directionText={panelData[0]?.direction}
          img={panelData[0]?.img}
          onClick={() => go(panelData[0]?.direction)}
        />
        <Panel
          position="middle"
          directionText={panelData[1]?.direction}
          img={panelData[1]?.img}
          onClick={() => go(panelData[1]?.direction)}
        />
        <Panel
          position="right"
          directionText={panelData[2]?.direction}
          img={panelData[2]?.img}
          onClick={() => go(panelData[2]?.direction)}
        />
      </div>

      {showBackButton && (
        <button id="back" onClick={() => go(backDirection)}>
          Go Back
        </button>
      )}
    </article>
  );
}

export default Runner;
