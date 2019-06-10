MACROS = __cpp_transactional_memory
OBJS = main.o
CFLAGS = -Wall -g -fgnu-tm
CXX = g++
INCLUDE =
LIBS = -lm

STM:${OBJS}
	${CXX} ${CFLAGS} ${INCLUDE} -o $@ ${OBJS} ${LIBS}

clean:
	-rm -f *.o core *.core

.cpp.o:
	${CXX} ${CFLAGS} ${INCLUDE} -c $<
