import React from "react";
import loadingImage from "../assets/loading-gif.webp";

function LoadingIndicator(props) {
  const imgStyle = {
    width: "100%",
    height: "100%",
  };

  return (
    <div {...props}>
      <img src={loadingImage} alt="loading" style={imgStyle} />
    </div>
  );
}

export default LoadingIndicator;
