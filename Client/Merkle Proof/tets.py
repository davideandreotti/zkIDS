

file = open('allowlist.txt', 'r')
lines = file.readlines()
file.close()
lines.sort()
lines = [line.rstrip('\n') for line in lines]
print(lines)
with open('allowlist.txt','w') as file:
	for i, item in enumerate(lines):
		if i != len(lines)-1:
			file.write(item + '\n')
		else:
			file.write(item)