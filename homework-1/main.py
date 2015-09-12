"""
A node in the graph.
"""
class Node(object):
  def __init__(self, name):
    self.name  = name
    self.paths = []

"""
A path between nodes.
"""
class Path(object):
  def __init__(self):
    print("Path")
    self.cost     = 0
    self.offtimes = []

class State(object):
  def __init__(self):
    print("State")

class Task(object):

  def __init__(self, file):
    self.algorithm    = readLine(file)
    
    self.source       = readLine(file)

    # Destinations
    self.destinations = []
    for name in readLine(file).split():
      self.destinations.append(Node(name))
      
    # The Nodes
    self.nodes = {}
    
    # -- Add the source
    self.nodes[self.source] = Node(self.source)

    # -- Add the destinations
    for node in self.destinations:
      self.nodes[node.name] = node
    
    # -- Add the middle nodes
    for name in readLine(file).split():
      self.nodes[name] = Node(name)

    self.pipes        = []

    # Number of Pipes
    numberOfPipes = int(readLine(file))

    # Read Pipes
    for i in range(1, numberOfPipes + 1):
      self.pipes.append(Pipe(file))
      #print("\tReading Pipe " + str(i) + " = " + readLine(file))
      
    # Start Time
    self.startTime = int(readLine(file))

    # Separator Line
    readLine(file)
    
  #end __init__
  
  def __str__(self):
    line  = "Task:\n"
    line += "\t   Algorithm = " + self.algorithm + "\n"
    line += "\t      Source = " + self.source + "\n"
    
    line += "\tDestinations:\n"
    for node in self.destinations:
      line += "\t\tName = " + node.name
    
    line += "\tNodes:\n"
    for node in self.nodes:
      line += "\t\tName = " + node.name
    
    line += "\t       Pipes = " + str(len(self.pipes)) + "\n"
    for p in self.pipes: line += p.__str__() + "\n"
    return(line)
  #end str

#end Task
    
  
class Pipe(object):
  
  def __init__(self, file):
    line = readLine(file).split()
    self.start    = line.pop(0)
    self.end      = line.pop(0)
    self.length   = line.pop(0)
    self.offTimes = []
    
    numberOfPeriods = int(line.pop(0))
    
    for _ in range(numberOfPeriods):
      period = line.pop(0).split("-")
      startTime = int(period[0])
      endTime = int(period[1])
      
      for i in range(startTime, endTime + 1):
        self.offTimes.append(i)
  #end __main__   

  def __str__(self):
    line  = "\tPipe:\n"
    line += "\t\t    Start = " + self.start + "\n"
    line += "\t\t      End = " + self.end + "\n"
    line += "\t\t   Length = " + self.length + "\n"
    line += "\t\tOff Times = " + ",".join(str(x) for x in self.offTimes)
    return line
  #end __str__
#end Pipe

def main():
  print("Starting...")
  
  # Open the file
  file = open("input.txt")
  
  # Read the number of tasks
  numberOfTasks = int(file.readline())
  print("Number of Tasks = " + str(numberOfTasks))
  
  for i in range(1, numberOfTasks + 1):
    print("Reading Task " + str(i))
    print(Task(file))
    
  # Close the file
  file.close()
  
#end main
    
def readLine(file):
  return file.readline().rstrip('\n')

#end readTask


if __name__ == "__main__":
  main()