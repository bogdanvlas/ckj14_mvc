let f = document.getElementById("form")

async function submitForm() {
	if (await validateForm()) {
		if (confirm("Complete registration?")) {
			f.url.value = window.location.href
			f.submit()
		}
	} else {
		alert("Validation failed!")
	}
}

async function validateForm() {
	let username = f.username
	let password = f.password
	let confirmPassword = f.confirmPassword
	let email = f.email

	let response = await fetch("/api/usernames")
	let usernames = await response.json()
	for (let i = 0; i < usernames.length; i++) {
		if(username.value==usernames[i]){
			alert("Such username exists!")
			username.focus()
			return false
		}
	}

	if (!/^[a-zA-Z0-9_]+$/.test(username.value)) {
		alert("Username is not correct!")
		username.focus()
		return false
	}
	if (!password.value.match("^[a-zA-Z0-9]{8,}$")) {
		alert("Password is not correct!")
		password.focus()
		return false
	}
	if (!confirmPassword.value.match("^[a-zA-Z0-9]{8,}$")) {
		alert("Confirm password is not correct!")
		confirmPassword.focus()
		return false
	}
	if (password.value != confirmPassword.value) {
		alert("Confirm password doesn't match'!")
		confirmPassword.focus()
		return false
	}
	if (!email.value.match("^[a-zA-Z0-9]+@[a-zA-Z0-9]+.[a-zA-Z0-9]+$")) {
		alert("Email is not correct!")
		email.focus()
		return false
	}
	return true
}