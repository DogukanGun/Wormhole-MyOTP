package utils

import (
	"fmt"
	"os"
	"strings"
)

func check(e error) {
	if e != nil {
		panic(e)
	}
}

func SetEnv(filename string) {
	data, err := os.ReadFile(filename)
	check(err)
	dataAsString := string(data)
	for index, line := range strings.Split(dataAsString, "\n") {
		line = strings.TrimSpace(line)
		lineArr := strings.SplitN(line, "=", 2)
		if len(lineArr) != 2 {
			fmt.Printf("Invalid line in file: %s at line %d \n", filename, index)
			continue
		}
		key := strings.TrimSpace(lineArr[0])
		value := strings.TrimSpace(lineArr[1])
		err = os.Setenv(key, value)
		check(err)
	}
}
