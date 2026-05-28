// function showTitle(id) {
//   return new Promise((resolve) => {
//     setTimeout(() => {
//       document.getElementById(id).style.visibility = "visible";
//       resolve();
//     }, 1000);
//   });
// }

// async function showTitles() {
//   try {
//     await showTitle("title1");
//     await showTitle("title2");
//     await showTitle("title3");
//     await showTitle("title4");
//   } catch (error) {
//     console.log("error from " + error);
//   }
// }

// showTitles();

function createAdder(x) {
  function inner(y) {
    return x + y;
  }

  return inner;
}

const add5 = createAdder(5); //intitial function that that adds 5 and is rememebred for later use

console.log(add5(3));


