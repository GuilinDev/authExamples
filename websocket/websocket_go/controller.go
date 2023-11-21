package websocket_go

import "net/http"

// Add this inside the main() function in the same file
http.HandleFunc("/open/socket/onReceive", func(w http.ResponseWriter, r *http.Request) {
	if err := r.ParseForm(); err != nil {
		http.Error(w, "ParseForm() error", http.StatusInternalServerError)
		return
	}
	id := r.FormValue("id")
	pwd := r.FormValue("pwd")

	// Validate the password (implement your logic here)
	if pwd == "your_secret_password" {
		message := []byte(id)
		hub.broadcast <- message
	}
})
