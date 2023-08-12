import React from "react";
import "./Panel.css";

/**
 * Panel component
 * @param {Object} props
 *      position: left, middle, right
 *      img: image to display in the panel
 *      directionText: directional info to display
 *      onClick: click event
 * @returns
 */
function Panel({ position, img, directionText, onClick }) {
  const classes = "panel " + position;

  return (
    <div className={classes}>
      {directionText && <h4 className="sign">{directionText}</h4>}
      <img src={img} onClick={onClick} alt="" />
    </div>
  );
}

export default Panel;
