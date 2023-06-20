import matplotlib.pyplot as plt
data1 = [1.35E+07, 3441843.11, 227021.3333, 56815.88889, 111059.8889, 59145.55556, 37.24]
data2 = [1.36E+07, 5244334.06, 230937.1111, 57660.33333, 113098.3333, 60178.44444, 42.35]
data3 = [1.35E+07, 5164599.03, 226829, 56921.66667, 110801.4444, 59105.88889, 44.88]
name = ["Revenue", "Losses", "PassangersTransported", "directRoutes", "TrainChanges", "EndingInSalzburg", "AvgHappiness"]

# Normalize the data
data1_norm = []
data2_norm = []
data3_norm = []

for i in range(len(name)):
    max_value = max([data1[i], data2[i], data3[i]])
    data1_norm.append(data1[i] / max_value)
    data2_norm.append(data2[i] / max_value)
    data3_norm.append(data3[i] / max_value)

# Create a list of pairs of names (first 4 characters)
name_pairs = [str(name[i][:8]) + "." for i in range(len(name))]

# Create a bar chart for all values
bar_width = 0.25
r1 = [0, 1, 2, 3, 4, 5, 6]
r2 = [x + bar_width for x in r1]
r3 = [x + bar_width for x in r2]

plt.bar(r1, data1_norm, color='red', width=bar_width, edgecolor='black', label='Policy 1')
plt.bar(r2, data2_norm, color='green', width=bar_width, edgecolor='black', label='Policy 2')
plt.bar(r3, data3_norm, color='blue', width=bar_width, edgecolor='black', label='Policy 3')

plt.xlabel('Data')
plt.ylabel('Normalized Value')
plt.xticks([r + bar_width for r in range(len(data1))], name_pairs)
plt.title('Comparison of all values (normalized)')
plt.legend()
plt.show()