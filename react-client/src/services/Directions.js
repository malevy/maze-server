const directions = ["north", "east", "south", "west"];
const directionsLen = directions.length;
const oppositeOffset = 2;

const funcs = {
  /**
   * getDirectionsComingFrom
   * @param {string} comingFrom - the origination point
   * @returns {string[]} - array of directions as they appear on a compass if observed coming
   *                          from the given direction
   */
  getDirectionsComingFrom(comingFrom) {
    const start = directions.findIndex((d) => d === comingFrom);
    let results = [];
    for (let offset = -1; offset <= 1; offset++) {
      results.push(
        directions[
          (((start + offset) % directionsLen) + directionsLen) % directionsLen
        ]
      );
    }
    return results;
  },

  /**
   * oppositeOf
   * @param {string} direction
   * @returns {string} the compass direction that is opposite of the given direction.
   * @example oppositeOf("north") => "south"
   */
  oppositeOf(direction) {
    const index = directions.findIndex((d) => d === direction);
    return directions[
      (((index + oppositeOffset) % directionsLen) + directionsLen) %
        directionsLen
    ];
  },

  EXIT: "EXIT",
};

export default funcs;
