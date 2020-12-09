package main

import (
	"bufio"
	"os"
	"strconv"
)

func readEveryLine(filename string) []string {
	lines := make([]string, 0)
	fileHandle, _ := os.Open(filename)
	defer fileHandle.Close()
	fileScanner := bufio.NewScanner(fileHandle)
	for fileScanner.Scan() {
		lines = append(lines, fileScanner.Text())
	}
	return lines
}

func readEveryLineInt64(filename string) []int64 {
	nums := make([]int64, 0)
	fileHandle, _ := os.Open(filename)
	defer fileHandle.Close()
	fileScanner := bufio.NewScanner(fileHandle)
	for fileScanner.Scan() {
		num, _ := strconv.ParseInt(fileScanner.Text(), 10, 64)
		nums = append(nums, num)
	}
	return nums
}
