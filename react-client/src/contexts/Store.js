import React, { useCallback } from "react";

const StoreContext = React.createContext();

export function StoreProvider({ children }) {
  const [currentCell, setCurrentCell] = React.useState([]);
  const [from, setFrom] = React.useState("");

  /**
   * storeNavigation
   * @param {Object} currentCell - the cell being entered
   * @param {*} fromDirection - the direction being used to enter cell, relative to the cell
   */
  const storeNavigation = useCallback(
    (currentCell, fromDirection) => {
      setCurrentCell(currentCell);
      setFrom(fromDirection);
    },
    [setCurrentCell, setFrom]
  );

  const contextValue = { currentCell, from, storeNavigation };
  return (
    <StoreContext.Provider value={contextValue}>
      {children}
    </StoreContext.Provider>
  );
}

export function useStore() {
  const context = React.useContext(StoreContext);
  if (!context) {
    throw new Error(
      "useStore must be used within the context of a StoreProvider"
    );
  }
  return context;
}
