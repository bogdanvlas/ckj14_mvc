async function getAll() {
	let response = await fetch("/notes/all")
	let notes = await response.json()
	console.log(notes)
}

async function getOne(id) {
	let response = await fetch("/notes/one/" + id)
	let note = await response.json()
	console.log(note)
}

async function postNote() {
	let title = prompt("Enter title:")
	let description = prompt("Enter description:")
	let note = { title: title, description: description }
	let jsonString = JSON.stringify(note)
	let response = await fetch("/notes/create", {
		method: "POST",
		headers: {
			"Content-Type": "application/json"
		},
		body: jsonString
	})
	note = await response.json()
	console.log(note)
}


async function putNote() {
	let id = prompt("Enter id:")
	let title = prompt("Enter title:")
	let description = prompt("Enter description:")
	let note = { id:id, title: title, description: description }
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

async function deleteNote(){
	let id = prompt("Enter id for delete")
	await fetch("/notes/delete/"+id,{
		method:"DELETE"
	})
}