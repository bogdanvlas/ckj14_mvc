let rootElement = document.getElementById("root")

function addNewElement(note) {
	let noteElement = document.createElement("div")
	let titleElement = document.createElement("a")
	titleElement.innerText = note.title
	titleElement.href = note.links[0].href
	let descriptionElement = document.createElement("div")
	descriptionElement.innerText = note.description
	let deleteButton = document.createElement("button")
	deleteButton.type = "button"
	deleteButton.innerText = "Delete"
	deleteButton.onclick = async function() {
		await deleteNote(note)
	}
	noteElement.appendChild(titleElement)
	noteElement.appendChild(descriptionElement)
	noteElement.appendChild(deleteButton)
	rootElement.appendChild(noteElement)
}

async function loadNotesPage() {
	let notes = await getAll()
	for (let i = 0; i < notes.length; i++) {
		addNewElement(notes[i])
	}
}

async function createNote() {
	let title = prompt("Enter title:")
	let description = prompt("Enter description:")
	let note = { title: title, description: description }
	note = await postNote(note)
	addNewElement(note)
}

loadNotesPage()
