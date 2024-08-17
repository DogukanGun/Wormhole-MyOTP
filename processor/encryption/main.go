package encryption

import (
	"crypto/aes"
	"crypto/cipher"
	"crypto/rand"
	"encoding/hex"
	"fmt"
	"io"
)

// Encrypt function
func Encrypt(key, text string) (string, error) {
	// Decode the key from hex to bytes
	keyBytes, err := hex.DecodeString(key)
	if err != nil {
		return "", err
	}

	// Ensure the key is a valid AES key length
	switch len(keyBytes) {
	case 16, 24, 32: // Valid lengths: 16 bytes (AES-128), 24 bytes (AES-192), 32 bytes (AES-256)
	default:
		return "", fmt.Errorf("invalid key size: must be 16, 24, or 32 bytes")
	}

	// Create AES cipher
	block, err := aes.NewCipher(keyBytes)
	if err != nil {
		return "", err
	}

	plaintext := []byte(text)
	ciphertext := make([]byte, aes.BlockSize+len(plaintext))

	iv := ciphertext[:aes.BlockSize]
	if _, err := io.ReadFull(rand.Reader, iv); err != nil {
		return "", err
	}

	stream := cipher.NewCFBEncrypter(block, iv)
	stream.XORKeyStream(ciphertext[aes.BlockSize:], plaintext)

	return hex.EncodeToString(ciphertext), nil
}

// Decrypt function
func Decrypt(key, cryptoText string) (string, error) {
	// Decode the key from hex to bytes
	keyBytes, err := hex.DecodeString(key)
	if err != nil {
		return "", err
	}

	// Ensure the key is a valid AES key length
	switch len(keyBytes) {
	case 16, 24, 32: // Valid lengths: 16 bytes (AES-128), 24 bytes (AES-192), 32 bytes (AES-256)
	default:
		return "", fmt.Errorf("invalid key size: must be 16, 24, or 32 bytes")
	}

	// Decode the ciphertext from hex to bytes
	ciphertext, err := hex.DecodeString(cryptoText)
	if err != nil {
		return "", err
	}

	block, err := aes.NewCipher(keyBytes)
	if err != nil {
		return "", err
	}

	if len(ciphertext) < aes.BlockSize {
		return "", fmt.Errorf("ciphertext too short")
	}

	iv := ciphertext[:aes.BlockSize]
	ciphertext = ciphertext[aes.BlockSize:]

	stream := cipher.NewCFBDecrypter(block, iv)
	stream.XORKeyStream(ciphertext, ciphertext)

	return string(ciphertext), nil
}
