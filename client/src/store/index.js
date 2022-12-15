
let currentCell = {};
let comingFrom = "";

export default {
    getCurrentCell() {
        return currentCell;
    },
    setCurrentCell(cell) {
        currentCell = cell;
    },
    getComingFrom() {
        return comingFrom;
    },
    setComingFrom(direction) {
        comingFrom = direction;
    }

};
