nums = [1,2,3,4,5]

-- concat
nums2 = nums ++ [6]
-- or
nums3 = 6:[1,2,3,4,5]

nums4 = [1,2..10]

-- to acess an element by index
secondElement = [9.4, 33.2, 2.9, 1.7, 8.7] !! 1

vet = [1,3..11]
hvet = head vet -- takes the first element
tvet = tail vet -- takes the elements from second
lvet = last vet -- takes the last element
ivet = init vet -- takes the elements until n-1
svet = length vet

-- list comprehesion
squareds = [x*x | x <- [1..10]]
squaredsGte36 = [x*x | x <- [1..10], x*x >= 36]