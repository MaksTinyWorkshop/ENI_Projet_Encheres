const sidebar = document.getElementById("side-bar");
const content = document.querySelector("main");

btn.addEventListener("click", () => {
	
	sidebar.classList.toggle("opened");
	btn.children[0].classList.toggle("opened");
	btn.children[1].classList.toggle("opened");
	btn.children[2].classList.toggle("opened");
});

content.addEventListener("click", () => {
	if (sidebar.classList.contains("opened")) {
		sidebar.classList.remove("opened");
		btn.children[0].classList.remove("opened");
		btn.children[1].classList.remove("opened");
		btn.children[2].classList.remove("opened");
	}
});