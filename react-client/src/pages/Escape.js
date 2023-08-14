import React from "react";
import "./Escape.css";
import Particles from "react-particles";
import { loadFireworksPreset } from "tsparticles-preset-fireworks";

function Escape() {
  const pInit = React.useCallback(async (engine) => {
    return loadFireworksPreset(engine);
  }, []);

  const pOptions = {
    preset: "fireworks",
  };

  return (
    <article>
      <Particles id="tsparticles" options={pOptions} init={pInit} />
      <h1 className="message">You've escaped!!</h1>
    </article>
  );
}

export default Escape;
