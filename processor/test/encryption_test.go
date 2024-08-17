package test

import (
	"crypto/ecdsa"
	"encoding/hex"
	"github.com/ethereum/go-ethereum/crypto"
	"github.com/stretchr/testify/assert"
	"log"
	"offchain_processor/encryption"
	"os"
	"testing"
)

func TestMustBeEncrypted(t *testing.T) {
	// Generate a new private key
	privateKey, err := crypto.GenerateKey()
	if err != nil {
		log.Fatal(err)
	}
	privateKeyBytes := crypto.FromECDSA(privateKey)
	privateKeyHex := hex.EncodeToString(privateKeyBytes)

	// Get the public key
	publicKey := privateKey.Public()
	publicKeyECDSA, ok := publicKey.(*ecdsa.PublicKey)
	address := crypto.PubkeyToAddress(*publicKeyECDSA).Hex()
	if !ok {
		log.Fatal("could not cast public key to ECDSA")
	}
	t.Setenv("TEXT", "This is test")
	t.Setenv("WALLET_ADDRESS", address)
	t.Setenv("PRIVATE_KEY", privateKeyHex)

	resFromEncryption, err := encryption.Encrypt(os.Getenv("PRIVATE_KEY"), os.Getenv("TEXT"))
	resFromDecryption, err := encryption.Decrypt(os.Getenv("WALLET_ADDRESS"), resFromEncryption)

	assert.Equal(t, os.Getenv("TEXT"), resFromDecryption)
}
