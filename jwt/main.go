package main

import (
	"fmt"
	"time"
)

func main() {
	// Generate a JWT
	tokenString, err := GenerateJWT()
	if err != nil {
		fmt.Println("Error generating token:", err)
		return
	}
	fmt.Println("Generated JWT:", tokenString)

	// Wait for a second to simulate some processing time
	time.Sleep(1 * time.Second)

	// Validate and extract data from the JWT
	claims, err := ValidateJWT(tokenString)
	if err != nil {
		fmt.Println("Error validating token:", err)
		return
	}
	fmt.Printf("Extracted claims from JWT: %+v\n", claims)
}
