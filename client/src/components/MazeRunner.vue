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
    <button id="back" @click="go(backwards.direction)">Go Back</button>
  </article>
</template>

<script>
import mazeserver from "@/gateways/mazeserver.js";
import store from "@/store";
import directions from "@/services/Directions";

const doorImages = [
  require("../assets/left-wall-door.png"),
  require("../assets/front-wall-door.png"),
  require("../assets/right-wall-door.png"),
];
const wallImages = [
  require("../assets/left-wall-blank.png"),
  require("../assets/front-wall-blank.png"),
  require("../assets/right-wall-blank.png"),
];

export default {
  name: "maze-runner",
  data() {
    return {
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
  },
  created() {
    this.render(store.getCurrentCell(), store.getComingFrom());
  },
};
</script>

<style scoped>
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
  top: 10.5rem;
  left: 10rem;
  transform: perspective(500px) scaleZ(2) rotateY(45deg);
}

#middlePanel .sign {
  top: 12rem;
  left: 6.5rem;
}

#rightPanel .sign {
  top: 10.5rem;
  left: 6rem;
  transform: perspective(500px) scaleZ(2) rotateY(-45deg);
}
</style>