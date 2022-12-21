<template>
  <article>
    <div id="view">
      <div class="panel" id="leftPanel">
        <h4 class="sign" v-if="panels[0].direction">
          {{ panels[0].direction }}
        </h4>
        <img :src="panels[0].img" @click="go(panels[0].direction)" alt="" />
      </div>
      <div class="panel" id="middlePanel">
        <h4 class="sign" v-if="panels[1].direction">
          {{ panels[1].direction }}
        </h4>
        <img :src="panels[1].img" alt="" @click="go(panels[1].direction)" />
      </div>
      <div class="panel" id="rightPanel">
        <h4 class="sign" v-if="panels[2].direction">
          {{ panels[2].direction }}
        </h4>
        <img :src="panels[2].img" alt="" @click="go(panels[2].direction)" />
      </div>
    </div>
    <button id="back" v-show="showBackButton" @click="go(backwards.direction)">
      Go Back
    </button>
  </article>
</template>

<script>
import mazeserver from "@/gateways/mazeserver.js";
import store from "@/store";
import directions from "@/services/Directions";

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

export default {
  name: "maze-runner",
  data() {
    return {
      showBackButton: true,
      panels: [
        {
          img: "",
          direction: "",
        },
      ],
      backwards: {
        direction: "",
      },
    };
  },
  methods: {
    go(direction) {
      if (!direction) return;

      if (direction === "exit") {
        this.$router.push({ name: "escaped" });
      }

      const link = store
        .getCurrentCell()
        .links.find((link) => link.rel === direction);

      if (!link) return;

      mazeserver.goToCell(link.href).then((cell) => {
        store.setComingFrom(direction);
        store.setCurrentCell(cell);
        this.render(cell, direction);
      });
    },
    render(cell, comingFrom) {
      if (this.hasExit(cell)) this.renderExit(cell);
      else this.renderCell(cell, comingFrom);
    },
    renderExit() {
      const newPanels = [];
      newPanels.push({ img: wallImages[0], direction: "" });
      newPanels.push({ img: doorImages[1], direction: "exit" });
      newPanels.push({ img: wallImages[2], direction: "" });
      this.showBackButton = false;
      this.panels = newPanels;
    },
    renderCell(cell, comingFrom) {
      const compassPaths = directions.getDirectionsComingFrom(comingFrom);
      const newPanels = [];
      for (let i = 0; i < 3; i++) {
        const link = cell.links.find((link) => link.rel == compassPaths[i]);
        if (link) {
          newPanels.push({ img: doorImages[i], direction: link.rel });
        } else {
          newPanels.push({ img: wallImages[i], direction: "" });
        }
      }
      this.backwards.direction = directions.oppositeOf(comingFrom);
      this.panels = newPanels;
    },
    hasExit(cell) {
      return cell.links.some((link) => link.rel === directions.EXIT);
    },
  },
  created() {
    this.render(store.getCurrentCell(), store.getComingFrom());
  },
};
</script>

<style scoped>
article {
  display: flex;
  flex-direction: column;
  align-content: center;
  width: fit-content;
}

#view {
  display: flex;
}

h4 {
  height: 1rem;
}

.panel {
  position: relative;
}

.sign {
  position: absolute;
  font-size: 1.5rem;
  width: 6rem;
  height: 2rem;
  text-align: center;
  background-color: aliceblue;
}

#leftPanel .sign {
  top: 10rem;
  left: 8rem;
  transform: perspective(500px) scaleZ(2) rotateY(45deg) rotateZ(10deg);
}

#middlePanel .sign {
  top: 11rem;
  left: 6rem;
}

#rightPanel .sign {
  top: 10.5rem;
  left: 6rem;
  transform: perspective(500px) scaleZ(2) rotateY(-45deg) rotateZ(-10deg);
}
</style>
