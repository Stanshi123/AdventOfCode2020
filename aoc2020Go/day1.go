package main

import "sort"

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
	for i, num := range nums[0: len(nums) - 2] {
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