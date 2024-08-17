package mongo

import "go.mongodb.org/mongo-driver/mongo"

type DbExecute struct {
	Client     *mongo.Client
	Database   string
	Collection string
}
