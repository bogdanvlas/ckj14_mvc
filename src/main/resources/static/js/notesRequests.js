async function getAll() {
	let response = await fetch("/notes/all")
	if (response.status == 200) {
		let notes = await response.json()
		return notes
	}
}

async function getOne(id) {
	let response = await fetch("/notes/one/" + id)
	let note = await response.json()
	console.log(note)
}

async function postNote(note) {
	let jsonString = JSON.stringify(note)
	let response = await fetch("/notes/create", {
		method: "POST",
		headers: {
			"Content-Type": "application/json"
		},
		body: jsonString
	})
	if (response.status == 200) {
		note = await response.json()
		return note
	}
	return false
}


async function putNote() {
	let id = prompt("Enter id:")
	let title = prompt("Enter title:")
	let description = prompt("Enter description:")
	let note = { id: id, title: title, description: description }
	let jsonString = JSON.stringify(note)
	let response = await fetch("/notes/change", {
		method: "PUT",
		headers: {
			"Content-Type": "application/json"
		},
		body: jsonString
	})
	note = await response.json()
	console.log(note)
}

async function deleteNote(note) {
	let url = note.links[2].href
	await fetch(url, {
		method: "DELETE"
	})
}

