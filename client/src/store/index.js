
let currentCell = {};
let comingFrom = "";
let mazeUrl = "";

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
    },
    get currentMazeUrl() {
        return mazeUrl;
    },
    set currentMazeUrl(url) {
        mazeUrl = url;
    }

};
