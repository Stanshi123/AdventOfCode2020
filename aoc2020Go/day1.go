package main

import (
	"sort"
)

type int64Arr []int64
func (e int64Arr) len() int           { return len(e) }
func (e int64Arr) less(i, j int) bool { return e[i] < e[j] }
func (e int64Arr) swap(i, j int)      { e[i], e[j] = e[j], e[i] }

func part1(nums []int64) int64 {
	set := make(map[int64]bool)
	for _, num := range nums {
		if _, exists := set[2020 - num]; exists {
			return (2020 - num) * num
		}
		set[num] = true
	}
	panic("No two entries that sum to 2020 found")
}

func part2(nums []int64) int64 {
	sort.Slice(nums, func(i, j int) bool { return nums[i] < nums[j]})
	for i, num := range nums {
		if i >= len(nums) - 2 { break }
		if i != 0 && nums[i - 1] == num { continue }
		j := i + 1
		k := len(nums) - 1
		for j < k {
			sum := num + nums[j] + nums[k]
			if sum == 2020 {
				return num * nums[j] * nums[k]
			} else if sum < 2020 {
				j++
			} else { k-- }
		}
	}
	panic("No triplets that sum to 2020 found")
}