"""
A node in the graph.
"""
class Node(object):
  def __init__(self, name):
    self.name  = name
    self.paths = []
  #end __init__
#end Node

"""
A path between nodes.
"""
class Path(object):
  def __init__(self):
    self.start    = None
    self.end      = None
    self.cost     = 0
    self.offtimes = []
  #end ___init__
#end Path

"""
An entire task. Includes algorithm, source, destination, and nodes.
"""
class Task(object):

  def __init__(self, file):
    self.algorithm    = readLine(file)
    self.source       = Node(readLine(file))

    # Destinations
    self.destinations = []
    for name in readLine(file).split():
      self.destinations.append(Node(name))
      
    # The Nodes
    self.nodes = {}
    
    # -- Add the source
    self.nodes[self.source.name] = self.source

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
    
      path = Path()
      line = readLine(file).split()
      path.start = line.pop(0)
      path.end   = line.pop(0)
      path.cost  = line.pop(0)
      
      numberOfPeriods = int(line.pop(0))

      for _ in range(numberOfPeriods):
        period    = line.pop(0).split("-")
        startTime = int(period[0])
        endTime   = int(period[1])
        
        for i in range(startTime, endTime + 1):
          path.offtimes.append(i)
        #end
      #end
        
      path.offtimes.sort()
      self.nodes[path.start].paths.append(path)
      
    #end
      
    # Start Time
    self.startTime = int(readLine(file))

    # Separator Line
    readLine(file)
    
  #end __init__
  
  def __str__(self):
    line  = "Task:\n"
    line += "\tAlgorithm = " + self.algorithm + "\n"
    line += "\tSource = " + self.source.name + "\n"
    
    line += "\tDestinations = "
    
    for node in self.destinations:
      line += node.name + ", "
    line += "\n"
    
    line += "\tNodes:\n"
    for name in self.nodes:
      node = self.nodes[name]
      line += "\t\t" + node.name + ":\n"

      for path in node.paths:
        line += "\t\t\tStart = " + path.start + "\n"
        line += "\t\t\t  End = " + path.end   + "\n"
        line += "\t\t\t Cost = " + path.cost  + "\n"
        line += "\t\t\t  Off = " + ", ".join(str(x) for x in path.offtimes) + "\n\n"

    return(line)
  #end __str__

#end Task

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