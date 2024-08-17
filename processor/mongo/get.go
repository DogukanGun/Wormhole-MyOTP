package mongo

import (
	"context"
	"fmt"
	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/mongo"
)

func (db *DbExecute) Get(filter bson.D, many bool, result []interface{}) (err error) {
	coll := db.Client.Database(db.Database).Collection(db.Collection)
	defer func(Client *mongo.Client, ctx context.Context) {
		err := Client.Disconnect(ctx)
		if err != nil {
			fmt.Println("Could not disconnect from mongo")
		}
	}(db.Client, context.TODO())
	if many {
		cursor, err := coll.Find(context.TODO(), filter)
		err = cursor.All(context.TODO(), &result)
		return err
	}
	err = coll.FindOne(context.TODO(), filter).Decode(&result)
	return
}
