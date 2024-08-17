package main

import (
	"fmt"
	"github.com/gin-gonic/gin"
	"go.mongodb.org/mongo-driver/bson"
	"net/http"
	"offchain_processor/mongo"
	"offchain_processor/registery/utils"
	"os"
)

type RegisterRequest struct {
	WalletAddress string `json:"wallet_address"`
	Token         string `json:"token"`
}

func main() {
	r := gin.Default()
	path, err := os.Getwd()
	utils.SetEnv(path + "/registery/utils/.env")
	db := mongo.DbExecute{
		Database:   "Keys",
		Collection: "Registries",
	}
	err = db.Connect()
	if err != nil {
		panic(err)
		return
	}
	r.POST("/register", func(c *gin.Context) {
		var req RegisterRequest
		err := c.BindJSON(&req)
		if err != nil {
			c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
			return
		}
		_, err = db.Insert(req)
		if err != nil {
			c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
			return
		}
		c.JSON(http.StatusOK, gin.H{
			"status": true,
		})
	})
	r.PATCH("/register", func(c *gin.Context) {
		var req RegisterRequest
		err := c.BindJSON(&req)
		if err != nil {
			c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
			return
		}
		_, err = db.Update(
			bson.D{{"token", req.Token}},
			bson.D{{"walletaddress", req.WalletAddress}},
		)
		if err != nil {
			c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
			return
		}
		c.JSON(http.StatusOK, gin.H{
			"status": true,
		})
	})
	err = r.Run()
	if err != nil {
		fmt.Printf("error while running: %v", err)
	}
}
