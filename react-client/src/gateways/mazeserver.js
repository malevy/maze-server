import axios from "axios";

const BASE_URL = "http://localhost:9000";

const client = axios.create({
  headers: {
    "Content-Type": "application/json",
  },
  baseURL: BASE_URL,
});

/**
 * generic GET that unrolls the Axios response object
 * and expects the full URL to be passed in
 * @param {string} url
 * @returns {Object} object response from the server
 */
const get = function (url) {
  return client.get(url).then((resp) => resp.data);
};

const endpoints = {
  /**
   * get a list of mazes from the server
   * @returns {Link[]} array of link objects representing mazes
   */
  getMazeList() {
    return client.get("/mazes").then((r) => r.data.collection.links);
  },

  /**
   *
   * @param {string} mazeUrl
   * @returns {Link} the link object representing the maze entrance.
   */
  getStartUrl(mazeUrl) {
    return get(mazeUrl).then((data) => data.item.link);
  },

  /**
   *
   * @param {string} URL of the cell to visit
   * @returns {object} the fetched cell
   */
  goToCell(cellUrl) {
    return get(cellUrl).then((data) => data.cell);
  },
};

export default endpoints;
